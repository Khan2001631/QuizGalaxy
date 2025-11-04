import { Outlet } from "react-router-dom";
import { Toaster } from "react-hot-toast";
import Header from "../components/Navigation/Header";

export default function RootPage(){
    return(
        <>
            <Header />
            <Toaster position="top-right" />
            <main>
                <Outlet></Outlet>
            </main>
        </>
    )
}