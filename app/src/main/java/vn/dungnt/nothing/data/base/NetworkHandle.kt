package vn.dungnt.nothing.data.base

import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response

suspend fun <T> getNetworkResult(networkCall: suspend () -> Response<ApiResponse<T>>): NetworkResult<T> {
    val response = networkCall.invoke()
    try {
        return if (response.isSuccessful) {
            checkNetworkResult(response.body())
        } else {
            getNetworkResultFailure(response)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        return NetworkResult.Failure(message = e.message.toString())
    }

}

private fun <T> checkNetworkResult(responseBody: ApiResponse<T>?): NetworkResult<T> {
    if (responseBody is ApiResponse<T>) {
        return NetworkResult.Success(data = responseBody.data?.user, message = responseBody.message)
    } else {
        return getNetworkResultFailure()
    }
}

private fun <T> getNetworkResultFailure(response: Response<ApiResponse<T>>): NetworkResult<T> {
    val errorString = response.errorBody()?.string() ?: "{}"
    val jsonObject = JSONObject(errorString)
    if (jsonObject.optString("description").isNotBlank()) {
        jsonObject.let {
            return (NetworkResult.Failure(
                message = it.optString("description"),
                exception = HttpException(response)
            ))
        }
    } else {
        val errorJson = JSONObject(errorString).getJSONObject("error")
        val message = errorJson.optString("message")
        return NetworkResult.Failure(
            message = message,
            exception = HttpException(response)
        )
    }
}

private fun <T> getNetworkResultFailure(message: String = "Unknown Error"): NetworkResult<T> {
    return NetworkResult.Failure(message)
}