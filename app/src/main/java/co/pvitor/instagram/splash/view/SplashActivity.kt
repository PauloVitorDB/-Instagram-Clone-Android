package co.pvitor.instagram.splash.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import co.pvitor.instagram.common.base.DependencyInjector
import co.pvitor.instagram.common.extensions.onAnimationEnd
import co.pvitor.instagram.databinding.ActivitySplashBinding
import co.pvitor.instagram.login.view.LoginActivity
import co.pvitor.instagram.main.view.MainActivity
import co.pvitor.instagram.splash.Splash
import co.pvitor.instagram.splash.presentation.SplashPresenter

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity(), Splash.View {

    override lateinit var presenter: Splash.Presenter
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)

        setContentView(binding.root)

        presenter = SplashPresenter(this, DependencyInjector.splashRepository())

        binding.imageViewSplashIcon.animate().apply {
            duration = 1000
            alpha(1.0f)
            setListener(onAnimationEnd {
                presenter.authenticated()
            })
            start()
        }

        binding.imageViewFrom.animate().apply {
            duration = 1000
            alpha(1.0f)
            start()
        }


    }

    override fun toLoginScreen() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        binding.imageViewSplashIcon.animate().apply {
            duration = 1000
            startDelay = 1000
            setListener(onAnimationEnd {
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, 0)
            })
            start()
        }
        hideFromIcon()
    }

    override fun toMainScreen() {

        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        binding.imageViewSplashIcon.animate().apply {
            duration = 1000
            startDelay = 1000
            alpha(0.0f)
            setListener(onAnimationEnd {
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, 0)
            })
            start()
        }
        hideFromIcon()

    }

    private fun hideFromIcon() {
        binding.imageViewFrom.animate().apply {
            duration = 1000
            startDelay = 1000
            alpha(0.0f)
            start()
        }
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

}