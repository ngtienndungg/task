package vn.dungnt.nothing.data.base

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.greenrobot.eventbus.EventBus
import vn.dungnt.nothing.R
import vn.dungnt.nothing.application
import vn.dungnt.nothing.data.models.EventType
import vn.dungnt.nothing.data.models.MessageEvent
import vn.dungnt.nothing.utils.Utils.getString
import vn.dungnt.nothing.utils.Utils.isNetworkAvailable
import vn.dungnt.nothing.utils.is401UnauthorizedError

fun <M> resultFlow(
    isLocal: Boolean = false,
    networkCall: suspend () -> NetworkResult<M>
): Flow<NetworkResult<M>> =
    flow {
        emit(NetworkResult.Loading())
        processResultFlow(networkCall, isLocal)
    }.flowOn(Dispatchers.IO)

private suspend fun <M> FlowCollector<NetworkResult<M>>.processResultFlow(
    networkCall: suspend () -> NetworkResult<M>,
    isLocal: Boolean
) {
    if (!isNetworkAvailable() && !isLocal) {
        emit(
            NetworkResult.Failure(
                message = getString(application!!, R.string.please_check_your_network)
            )
        )
    } else when (val responseStatus = networkCall.invoke()) {
        is NetworkResult.Success -> {
            Log.d("SingleSource", "processResultFlow: emitted")
            emit(
                NetworkResult.Success(
                    responseStatus.message,
                    responseStatus.data
                )
            )
        }

        is NetworkResult.Failure -> {
            when {
                responseStatus.exception?.is401UnauthorizedError() == true -> {
                    EventBus.getDefault().post(MessageEvent(EventType.CLEAR_DATA_GO_TO_LOGIN))
                }

                else -> {
                    emit(
                        NetworkResult.Failure(
                            message = responseStatus.message,
                            exception = responseStatus.exception,
                            data = responseStatus.data
                        )
                    )
                }
            }
        }

        else -> {}
    }
}