import { Form, NavLink } from "react-router-dom";

const SignIn = () => {
  return (
    <div className="min-h-screen flex items-center justify-center bg-gradient-to-br from-blue-50 to-indigo-100 p-4">
      <div className="w-full max-w-md bg-white rounded-3xl shadow-xl overflow-hidden border border-gray-100">
        {/* Top content */}
        <div className="p-8">
          {/* Badge */}
          <div className="flex justify-center mb-4">
            <span className="px-3 py-1 bg-indigo-100 text-indigo-700 text-xs font-semibold rounded-full tracking-wide uppercase">
              Quiz Galaxy
            </span>
          </div>

          <h1 className="text-3xl font-extrabold text-gray-800 text-center mb-2 tracking-tight">
            Welcome Back
          </h1>
          <p className="text-center text-gray-500 text-sm mb-8 leading-relaxed">
            Sign in to{" "}
            <span className="font-semibold text-indigo-500">Quiz Galaxy</span>{" "}
            and continue your journey.
          </p>

          {/* React Router Form */}
          <Form method="post" className="space-y-6">
            {/* Email */}
            <div>
              <label
                htmlFor="email"
                className="block text-sm font-medium text-gray-700 mb-1"
              >
                Email address
              </label>
              <input
                id="email"
                type="email"
                name="email"
                required
                className="w-full px-4 py-2.5 border border-gray-300 rounded-lg shadow-sm focus:outline-none focus:ring-2 focus:ring-indigo-400 focus:border-indigo-400 text-sm"
              />
            </div>

            {/* Password */}
            <div>
              <label
                htmlFor="password"
                className="block text-sm font-medium text-gray-700 mb-1"
              >
                Password
              </label>
              <input
                id="password"
                type="password"
                name="password"
                required
                className="w-full px-4 py-2.5 border border-gray-300 rounded-lg shadow-sm focus:outline-none focus:ring-2 focus:ring-indigo-400 focus:border-indigo-400 text-sm"
              />
            </div>

            {/* Sign in button */}
            <button
              type="submit"
              className="w-full py-3 bg-gradient-to-r from-indigo-500 to-indigo-600 hover:from-indigo-600 hover:to-indigo-700 text-white font-semibold rounded-lg shadow-md transition-all focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-400"
            >
              Sign in
            </button>
          </Form>
        </div>

        {/* Bottom link */}
        <div className="bg-gray-50 px-6 py-5 text-center text-sm text-gray-700 border-t border-gray-200">
          <span className="mr-1">New to Quiz Galaxy?</span>
          <NavLink
            to="/auth?mode=SignUp"
            className="text-indigo-600 font-medium underline hover:text-indigo-800 transition-colors"
          >
            Create account
          </NavLink>
        </div>
      </div>
    </div>
  );
};

export default SignIn;
