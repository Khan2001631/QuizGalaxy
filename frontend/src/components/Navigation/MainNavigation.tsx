import { NavLink } from "react-router-dom";

export default function MainNavigation() {
  return (
    <header className="bg-gradient-to-r from-orange-50 to-pink-50 shadow-md">
      <nav className="container mx-auto flex items-center justify-between px-6 py-4">
        {/* Brand name */}
        <div className="text-2xl font-extrabold bg-gradient-to-r from-orange-500 to-pink-500 bg-clip-text text-transparent tracking-wide">
          Quiz Galaxy
        </div>

        {/* Navigation links */}
        <ul className="flex items-center space-x-8">
          <li>
            <NavLink
              to="/"
              className={({ isActive }) =>
                `text-base font-medium transition-colors duration-300 ${
                  isActive
                    ? "text-orange-600 border-b-2 border-orange-500 pb-1"
                    : "text-gray-700 hover:text-orange-500"
                }`
              }
            >
              Home
            </NavLink>
          </li>
          <li>
            <NavLink
              to="/auth?mode=SignIn"
              className={({ isActive }) =>
                `text-base font-medium transition-colors duration-300 ${
                  isActive
                    ? "text-orange-600 border-b-2 border-orange-500 pb-1"
                    : "text-gray-700 hover:text-orange-500"
                }`
              }
            >
              Sign in
            </NavLink>
          </li>
        </ul>
      </nav>
    </header>
  );
}
