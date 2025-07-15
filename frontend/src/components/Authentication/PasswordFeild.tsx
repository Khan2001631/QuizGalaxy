import { useState, useEffect } from "react";
import { ExclamationCircleIcon, CheckCircleIcon } from "@heroicons/react/24/outline";

interface PasswordFieldProps {
  actionData?: {
    errors?: {
      password?: string;
    };
  };
}

export default function PasswordField({ actionData }: PasswordFieldProps) {
  const [password, setPassword] = useState("");
  const [strength, setStrength] = useState(0);
  const [showStrength, setShowStrength] = useState(false);

  // calculate strength
  const calculateStrength = (pwd: string): number => {
    if (!pwd) return 0;
    if (pwd.length < 8) return 1; // too weak
    if (pwd.length < 12) return 2; // acceptable
    return 3; // strong
  };

  useEffect(() => {
    const delay = setTimeout(() => {
      setStrength(calculateStrength(password));
      setShowStrength(password.length > 0);
    }, 500); // wait 0.5s after typing

    return () => clearTimeout(delay);
  }, [password]);

  const strengthLabel =
    strength === 1
      ? "Too weak"
      : strength === 2
      ? "Acceptable"
      : strength === 3
      ? "Strong"
      : "";

  const strengthMessage =
    strength === 1
      ? "Passwords need to be at least 8 characters. Include multiple words and phrases to make it more secure."
      : strength === 2
      ? "Include multiple words and phrases to make it more secure."
      : strength === 3
      ? "Great job! Your password is strong."
      : "";

  return (
    <div>
      <div className="flex justify-between items-center mb-1">
        <label className="block text-sm font-medium text-gray-700">Password</label>
        {showStrength && (
          <span
            className={`text-sm font-medium ${
              strength === 1
                ? "text-red-500"
                : strength === 2
                ? "text-yellow-600"
                : "text-green-600"
            }`}
          >
            {strengthLabel}
          </span>
        )}
      </div>

      <input
        type="password"
        name="password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
        className={`w-full px-4 py-2.5 border rounded-lg shadow-sm focus:outline-none focus:ring-2 text-sm transition-all ${
          strength === 1 && showStrength
            ? "border-red-500 focus:ring-red-400"
            : strength === 2
            ? "border-yellow-500 focus:ring-yellow-400"
            : strength === 3
            ? "border-green-500 focus:ring-green-400"
            : "border-gray-300 focus:ring-indigo-400"
        }`}
      />

      {/* Strength bars */}
      {showStrength && (
        <div className="flex mt-3 space-x-1">
          {[1, 2, 3].map((level) => (
            <div
              key={level}
              className={`h-1.5 flex-1 rounded-full transition-all ${
                strength >= level ? "bg-green-500" : "bg-gray-300"
              }`}
            ></div>
          ))}
        </div>
      )}

      {/* Strength helper message */}
      {showStrength && strengthMessage && (
        <p
          className={`mt-2 text-sm flex items-start space-x-1 ${
            strength === 1 ? "text-red-500" : "text-gray-600"
          }`}
        >
          {strength === 1 ? (
            <ExclamationCircleIcon className="h-4 w-4 mt-0.5 flex-shrink-0" />
          ) : (
            <CheckCircleIcon className="h-4 w-4 mt-0.5 flex-shrink-0 text-green-600" />
          )}
          <span>{strengthMessage}</span>
        </p>
      )}

      {/* Server-side error */}
      {actionData?.errors?.password && (
        <p className="text-red-500 text-sm mt-1">{actionData.errors.password}</p>
      )}
    </div>
  );
}
