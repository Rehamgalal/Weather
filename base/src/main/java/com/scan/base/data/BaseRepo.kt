package android.ptc.com.ptcflixing.base.data

import com.scan.base.utils.NetworkConnectivityHelper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

open class BaseRepo(
    private val networkConnectivityHelper: NetworkConnectivityHelper,
    val ioDispatcher: CoroutineDispatcher

) {

    protected fun <T> networkOnlyFlow(remoteCall: suspend () -> T): Flow<Resource<T>> {
        suspend fun fetchFromNetwork(): T {
            if (networkConnectivityHelper.isConnected()) return remoteCall()
            else throw NoInternetException()
        }

        return flow<Resource<T>> {
            emit(Resource.Loading)

            try {
                emit(Resource.Success(fetchFromNetwork()))
            } catch (e: RuntimeException) {
                handleApiException(e, this)
            }
        }.flowOn(Dispatchers.IO)
    }

    protected fun <T> networkOnlyFlowAndStore(
        remoteCall: suspend () -> T,
        localCall: suspend (T) -> Unit
    ): Flow<Resource<T>> {
        suspend fun fetchFromNetwork(): T {
            if (networkConnectivityHelper.isConnected()) {
                val response = remoteCall()
                localCall(response)
                return response
            } else throw NoInternetException()
        }

        return flow<Resource<T>> {
            emit(Resource.Loading)

            try {
                emit(Resource.Success(fetchFromNetwork()))
            } catch (e: RuntimeException) {
                handleApiException(e, this)
            }
        }.flowOn(Dispatchers.IO)
    }

    private suspend fun <T> handleApiException(
        e: RuntimeException,
        flowCollector: FlowCollector<Resource<T>>
    ) {
        e.printStackTrace()
        flowCollector.emit(Resource.Error(e))
    }

    inline fun <reified T> localOnlyFlow(crossinline localCall: suspend () -> T): Flow<Resource<T>> {
        return flow {
            emit(Resource.Loading)
            try {
                val data = localCall()
                emit(Resource.Success(data))
            } catch (e: RuntimeException) {
                e.printStackTrace()
                emit(Resource.Error(e))
            }
        }.flowOn(ioDispatcher)
    }
}