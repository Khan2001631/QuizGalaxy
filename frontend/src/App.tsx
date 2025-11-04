import { createBrowserRouter, RouterProvider } from "react-router-dom";
import HomePage from "./pages/Home";
import AuthPage, { action as AuthAction } from "./pages/AuthPage";
import RootPage from "./pages/RootPage";
import QuizSetupPage from "./pages/QuizSetupPage";
import QuizPage from './pages/QuizPage'

const App = () => {
    const route = createBrowserRouter([
    {
        path: "/",
        element: <RootPage />,
        children: [
            {
                path: "/",
                element: <HomePage />,
            },
            {
                path: "/auth",
                element: <AuthPage />,
                action: AuthAction
            },
            {
                path: "/quiz",
                element: <QuizSetupPage />
            },
            {
                path: "/quiz/start", // ✅ Register this new route
                element: <QuizPage />
            }
        ],
    },
    ]);
    return <RouterProvider router={route} />;
}

export default App;
