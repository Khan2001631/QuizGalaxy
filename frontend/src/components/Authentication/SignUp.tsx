import { Form, NavLink, useActionData } from "react-router-dom";
import PasswordField from "./PasswordFeild";

interface ActionErrors {
  errors?: {
    password?: string;
    email?: string;
    firstName?: string;
    lastName?: string;
    gender?: string;
  };
}

export default function SignUp() {
  const actionData = useActionData() as ActionErrors | undefined;
  

  // Determine strength
  

  

  

  return (
    <div className="min-h-screen flex items-center justify-center bg-gradient-to-br from-blue-50 to-indigo-100 p-4">
      <div className="w-full max-w-md bg-white rounded-3xl shadow-xl overflow-hidden border border-gray-100">
        <div className="p-8">
          <div className="flex justify-center mb-4">
            <span className="px-3 py-1 bg-indigo-100 text-indigo-700 text-xs font-semibold rounded-full tracking-wide uppercase">
              Quiz Galaxy
            </span>
          </div>
          <h1 className="text-3xl font-extrabold text-gray-800 mb-2 text-center tracking-tight">
            Create Your Account
          </h1>
          <p className="text-center text-gray-500 text-sm mb-8 leading-relaxed">
            Join <span className="font-semibold text-indigo-500">Quiz Galaxy</span> 
            &nbsp;to host quizzes or challenge your friends in seconds.
          </p>

          <Form method="post" className="space-y-6">
            {/* First & Last Name */}
            <div className="grid grid-cols-2 gap-4">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">First name</label>
                <input
                  type="text"
                  name="firstName"
                  required
                  className="w-full px-4 py-2.5 border border-gray-300 rounded-lg shadow-sm focus:outline-none focus:ring-2 focus:ring-indigo-400 text-sm"
                />
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Last name</label>
                <input
                  type="text"
                  name="lastName"
                  required
                  className="w-full px-4 py-2.5 border border-gray-300 rounded-lg shadow-sm focus:outline-none focus:ring-2 focus:ring-indigo-400 text-sm"
                />
              </div>
            </div>

            {/* Email */}
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Email</label>
              <input
                type="email"
                name="email"
                required
                className="w-full px-4 py-2.5 border border-gray-300 rounded-lg shadow-sm focus:outline-none focus:ring-2 focus:ring-indigo-400 text-sm"
              />
              {actionData?.errors?.email && (
                <p className="text-red-500 text-sm mt-1">{actionData.errors.email}</p>
              )}
            </div>

            <PasswordField actionData={actionData}/>

            {/* Gender */}
            <div>
              <span className="block text-sm font-medium text-gray-700 mb-2">Gender</span>
              <div className="flex space-x-3">
                <label className="cursor-pointer flex-1">
                  <input type="radio" name="gender" value="male" className="hidden peer" required />
                  <span className="block text-center px-4 py-2 rounded-lg border border-gray-300 text-sm peer-checked:bg-indigo-500 peer-checked:text-white">
                    Male
                  </span>
                </label>
                <label className="cursor-pointer flex-1">
                  <input type="radio" name="gender" value="female" className="hidden peer" required />
                  <span className="block text-center px-4 py-2 rounded-lg border border-gray-300 text-sm peer-checked:bg-indigo-500 peer-checked:text-white">
                    Female
                  </span>
                </label>
              </div>
            </div>

            {/* Submit Button */}
            <button
              type="submit"
          
              className={`w-full py-3 font-semibold rounded-lg shadow-md transition-all focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-400 ${
                
                  
                   "bg-gradient-to-r from-indigo-500 to-indigo-600 hover:from-indigo-600 hover:to-indigo-700 text-white"
              }`}
            >
              Create account
            </button>
          </Form>
        </div>

        {/* Bottom link */}
        <div className="bg-gray-50 px-6 py-5 text-center text-sm text-gray-700 border-t border-gray-200">
          <span className="mr-1">Already have an account?</span>
          <NavLink
            to="/auth?mode=SignIn"
            className="text-indigo-600 font-medium underline hover:text-indigo-800"
          >
            Sign in
          </NavLink>
        </div>
      </div>
    </div>
  );
}
