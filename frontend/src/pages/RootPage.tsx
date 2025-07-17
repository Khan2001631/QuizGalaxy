import { Outlet } from "react-router-dom";
import Header from "../components/Navigation/Header";

export default function RootPage(){
    return(
        <>
            <Header />
            <main>
                <Outlet></Outlet>
            </main>
        </>
    )
}