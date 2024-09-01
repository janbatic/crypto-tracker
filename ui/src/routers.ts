
import SignUp from "./view/SignUpView.vue"
import LoginView from "./view/LoginView.vue"
import HomeView from "./view/HomeView.vue"
import Profile from "./view/Profile.vue"
import {createRouter, createWebHistory} from "vue-router"

const routes =[
    {
        name: "Signup",
        component: SignUp,
        path: "/sign-up",
    },
    {
        name: "LoginView",
        component: LoginView,
        path: "/login",
    },
    {
        name: "HomeView",
        component: HomeView,
        path: "/",
    },
    {
        name: "Profile",
        component: Profile,
        path: "/profile",
    },

]

const routers = createRouter(
    {
        history:createWebHistory(),
        routes
    }
)
export default routers

