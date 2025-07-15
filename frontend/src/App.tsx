import { createBrowserRouter, RouterProvider } from "react-router-dom";
import "./App.css";
import HomePage from "./pages/Home";
import AuthPage, { action as AuthAction } from "./pages/AuthPage";
import RootPage from "./pages/RootPage";

function App() {
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
      ],
    },
  ]);
  return <RouterProvider router={route} />;
}

export default App;
