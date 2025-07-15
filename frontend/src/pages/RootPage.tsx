import { Outlet } from "react-router-dom";
import MainNavigation from "../components/Navigation/MainNavigation";

export default function RootPage(){
    return(
        <>
            <MainNavigation />
            <main>
                <Outlet></Outlet>
            </main>
        </>
    )
}