import { redirect, type ActionFunctionArgs } from "react-router-dom";
import AuthForm from "../components/Authentication/AuthForm";

export default function AuthPage() {
  return <AuthForm />;
}

function isInvalidText(value: FormDataEntryValue | null): boolean {
  return !value || value.toString().trim() === "";
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

  // We'll collect errors here
  const errors: Record<string, string> = {};

  if (mode === "SignUp") {
    const firstName = formData.get("firstName");
    const lastName = formData.get("lastName");
    const email = formData.get("email");
    const password = formData.get("password");
    const gender = formData.get("gender");

    // Validate fields
    if (isInvalidText(firstName)) errors.firstName = "First name is required";
    if (isInvalidText(lastName)) errors.lastName = "Last name is required";
    if (isInvalidText(email)) errors.email = "Email is required";
    if (isInvalidText(password)) errors.password = "Password is required";
    if (isInvalidText(gender)) errors.gender = "Please select a gender";
    if (typeof email === "string") {
      if (!email.includes("@")) {
        errors.email = "Invalid email";
      }
      if(!email.includes(".com")){
        errors.email = "Invalid email"
      }
    }
    
    if(typeof password === "string"){
      if(password.length < 8){
        errors.password = "Weak password"
      }
      
    }
    

    // If there are errors, return them
    if (Object.keys(errors).length > 0) {
      return  {errors} ;
    }

    // ✅ No errors: submit to backend
    const data = {
      firstName: firstName as string,
      lastName: lastName as string,
      email: email as string,
      password: password as string,
      gender: gender as string,
    };

    //send to backend 
    

    return redirect("/")
     // You can redirect or show success message
  }

  if (mode === "SignIn") {
    const email = formData.get("email");
    const password = formData.get("password");

    if (isInvalidText(email)) errors.email = "Email is required";
    if (isInvalidText(password)) errors.password = "Password is required";

    if (Object.keys(errors).length > 0) {
      return { errors };
    }

    const data = {
      email: email as string,
      password: password as string,
    };

    //  authenticate with backend
    
    
    return { success: true };
  }
}
