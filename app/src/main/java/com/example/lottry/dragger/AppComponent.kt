package com.example.lottry.dragger

import com.example.lottry.ui.activity.login.LoginActivity
import com.example.lottry.ui.activity.get_start.Get_Started_Activity
import com.example.lottry.ui.activity.login.LoginViewModel
import com.example.lottry.ui.activity.main.MainActivity
import com.example.lottry.ui.activity.main.MainViewModel
import com.example.lottry.ui.activity.registration.RegistrationActivity
import com.example.lottry.ui.activity.registration.RegistrationViewModel
import com.example.lottry.ui.activity.splash.SplashActivity
import com.example.lottry.ui.fragment.buy_ticket.Fragment_Buy_Ticket
import com.example.lottry.ui.fragment.buy_ticket.Fragment_Buy_Ticket_viewModel
import com.example.lottry.ui.fragment.home.Fragment_Home
import com.example.lottry.ui.fragment.home.Fragment_Home_viewModel
import com.example.lottry.ui.fragment.my_ticket.Fragment_My_Ticket
import com.example.lottry.ui.fragment.my_ticket.Fragment_My_Ticket_viewModel
import com.example.lottry.ui.fragment.notification.Fragment_Notification_viewModel
import com.example.lottry.ui.fragment.payment_gateway.Fragment_Payment_Gateway
import com.example.lottry.ui.fragment.payment_gateway.Fragment_Payment_Gateway_viewModel
import com.example.lottry.ui.fragment.tran_history.Trans_History_Fragment_viewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component (modules = [AppModule::class,PrimitivesModule::class,NetworkModule::class,UtilityModule::class])
interface AppComponent {

    fun inject (login: LoginActivity)
    fun inject(splash:SplashActivity)
    fun inject(get_started:Get_Started_Activity)
    fun inject(registeration:RegistrationActivity)
    fun inject(mainactivity: MainActivity)
    fun inject(mainViewModel: MainViewModel)
    fun inject(loginViewModel: LoginViewModel)
    fun inject(registrationViewModel: RegistrationViewModel)
    fun inject(home: Fragment_Home)
    fun inject(fragmentHomeViewmodel: Fragment_Home_viewModel)
    fun inject(fragmentBuyTicket: Fragment_Buy_Ticket)
    fun inject(fragmentBuyTicketViewmodel: Fragment_Buy_Ticket_viewModel)
    fun inject(fragmentMyTicket: Fragment_My_Ticket)
    fun inject(fragmentMyTicketViewmodel: Fragment_My_Ticket_viewModel)
    fun inject(fragmentPaymentGateway: Fragment_Payment_Gateway)
    fun inject(fragmentPaymentGatewayViewmodel: Fragment_Payment_Gateway_viewModel)
    fun inject(transHistoryFragmentViewmodel: Trans_History_Fragment_viewModel)
    fun inject(fragmentNotificationViewmodel: Fragment_Notification_viewModel)
}