import HomeView from "./components/HomeView.vue"
import LoginComponent from "./components/Login.vue"
import SignUp from "./components/SignUp.vue"
import {createRouter, createWebHistory} from "vue-router"

const routes =[
    {
        name: "HomeView",
        component: HomeView,
        path: "/"
    },
    {
        name: "Login",
        component: LoginComponent,
        path: "/login"
    },
    {
        name: "Signup",
        component: SignUp,
        path: "/sign-up"
    },

]

const router = createRouter(
    {
        history:createWebHistory(),
        routes
    }
)
export default router
