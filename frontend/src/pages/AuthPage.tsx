import { redirect, type ActionFunctionArgs } from "react-router-dom";
import AuthForm from "../components/Authentication/AuthForm";
import { getCurrentUser, loginUser, registerUser } from "../api/authAPI";

export default function AuthPage() {
  return <AuthForm />;
}

export async function action({ request }: ActionFunctionArgs) {
  const searchParams = new URL(request.url).searchParams;
  const mode = searchParams.get("mode") || "SignUp";

  if (mode !== "SignIn" && mode !== "SignUp") {
    return {
      errors: { general: "Unsupported mode" },
    };
  }

  const formData = await request.formData();
  const errors: Record<string, string> = {};

  if (mode === "SignUp") {
    const firstName = formData.get("firstName");
    const lastName = formData.get("lastName");
    const email = formData.get("email");
    const password = formData.get("password");
    const gender = formData.get("gender");

    // Validations...
    if (!firstName || firstName.toString().trim() === "") {
      errors.firstName = "First name is required";
    }
    if (!lastName || lastName.toString().trim() === "") {
      errors.lastName = "Last name is required";
    }
    if (!email || email.toString().trim() === "") {
      errors.email = "Email is required";
    } else {
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      if (!emailRegex.test(email.toString())) {
        errors.email = "Invalid email";
      }
    }
    if (!password || password.toString().trim().length < 8) {
      errors.password = "Password must be at least 8 characters";
    }

    if (!gender) {
      errors.gender = "Gender is required";
    }

    if (Object.keys(errors).length > 0) {
      return { errors };
    }

    const registerPayload = {
      username: email as string, // or you can let user enter it
      email: email as string,
      password: password as string,
      firstName: firstName as string,
      lastName: lastName as string,
      gender: gender as string,
    };

    try {
      await registerUser(registerPayload);
      return redirect('/');
    } catch (err: any) {
      return {
        errors: {
          general:
            err?.response?.data?.message ||
            "Something went wrong during registration",
        },
      };
    }
  }

  if (mode === "SignIn") {
    const email = formData.get("email");
    const password = formData.get("password");

    if (!email || email.toString().trim() === "") {
      errors.email = "Email is required";
    }
    if (!password || password.toString().trim() === "") {
      errors.password = "Password is required";
    }

    if (Object.keys(errors).length > 0) {
      return { errors };
    }

    try {
      await loginUser({
        email: email as string,
        password: password as string,
      });
      return redirect('/');
    } catch (err: any) {
      return {
        errors: {
          general:
            err?.response?.data?.message || "Invalid email or password",
        },
      };
    }
  }
}
