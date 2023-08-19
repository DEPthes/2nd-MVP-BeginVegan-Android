package com.example.beginvegan.src.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.beginvegan.config.ApplicationClass
import com.example.beginvegan.config.BaseActivity
import com.example.beginvegan.databinding.ActivityLoginBinding
import com.example.beginvegan.src.data.model.auth.Auth
import com.example.beginvegan.src.data.model.auth.AuthResponse
import com.example.beginvegan.src.data.model.auth.AuthSignUpInterface
import com.example.beginvegan.src.data.model.auth.AuthSignUpService
import com.example.beginvegan.util.Constants.ACCESS_TOKEN
import com.example.beginvegan.util.Constants.REFRESH_TOKEN
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient

class LoginActivity : BaseActivity<ActivityLoginBinding>({ActivityLoginBinding.inflate(it)}),AuthSignUpInterface{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    private  val mCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e("KaKao Login | CallBack", "로그인 실패 $error")
            Toast.makeText(this,"카카오 로그인 실패",Toast.LENGTH_SHORT).show()
        } else if (token != null) {
            Log.e("KaKao Login | CallBack", "로그인 성공 ${token.accessToken}")
            showLoadingDialog(this)
            getKakaoUserData()

        }
    }

    override fun init() {

        binding.btnLoginKakao.setOnClickListener{
            // 이메일 로그인 콜백
// 카카오톡 설치 확인
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                // 카카오톡 로그인
                UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                    // 로그인 실패 부분
                    if (error != null) {
                        Log.e("KaKao Login", "로그인 실패 $error")
                        // 사용자가 취소
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled ) {
                            Log.e("KaKao Login", "로그인 실패 사용자 취소")
                            return@loginWithKakaoTalk
                        }
                        // 다른 오류
                        else {
                            UserApiClient.instance.loginWithKakaoAccount(this, callback = mCallback) // 카카오 이메일 로그인
                            Log.e("KaKao Login", "예외 오류")
                        }
                    }
                    // 로그인 성공 부분
                    else if (token != null) {
                        Log.e("KaKao Login", "로그인 성공 ${token.accessToken}")
                        showLoadingDialog(this)
                        getKakaoUserData()
                    }
                }
            } else {
                Log.e("KaKao Login", "이메일 로그인")
                UserApiClient.instance.loginWithKakaoAccount(this, callback = mCallback) // 카카오 이메일 로그인
            }
        }
    }
    private fun getKakaoUserData(){
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e("KaKao User", "사용자 정보 요청 실패", error)
            }
            else if (user != null) {
                Log.i("KaKao User", "사용자 정보 요청 성공" +
                        "\n회원번호: ${user.id}" +
                        "\n이메일: ${user.kakaoAccount?.email}" +
                        "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                        "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}")
                val auth = Auth(
                    (user.id).toString()!!,
                    user.kakaoAccount?.profile?.nickname!!,
                    user.kakaoAccount?.email!!,
                    user.kakaoAccount?.profile?.thumbnailImageUrl!!)
                AuthSignUpService(this).tryPostAuthSignUp(auth)
            }
        }
    }
    private fun moveToMain(){
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onPostAuthSignUpSuccess(response: AuthResponse) {
        Log.d("access",response.accessToken)
        Log.d("refresh",response.refreshToken)
        Log.d("tokenType",response.tokenType)
        ApplicationClass.sSharedPreferences.edit().putString(ACCESS_TOKEN,response.accessToken)
        ApplicationClass.sSharedPreferences.edit().putString(REFRESH_TOKEN,response.refreshToken)
        moveToMain()
    }

    override fun onPostAuthSignUpFailed(message: String) {
        Log.d("onPostAuthSignUpFailed",message)
    }

}