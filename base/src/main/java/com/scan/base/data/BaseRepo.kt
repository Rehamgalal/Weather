package android.ptc.com.ptcflixing.base.data

import com.scan.base.utils.NetworkConnectivityHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

open class BaseRepo(
    private val networkConnectivityHelper: NetworkConnectivityHelper
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

    private suspend fun <T> handleApiException(
        e: RuntimeException,
        flowCollector: FlowCollector<Resource<T>>
    ) {
        e.printStackTrace()
        flowCollector.emit(Resource.Error(e))
    }
}