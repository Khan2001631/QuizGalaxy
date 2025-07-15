
import {  useSearchParams } from "react-router-dom";
import SignIn from "./SignIn";
import SignUp from "./SignUp";


const AuthForm = () => {
  const [searchParams] = useSearchParams();
  const isSignIn = searchParams.get("mode") === "SignIn";

  
  return isSignIn ? <SignIn/>: <SignUp />
  
};

export default AuthForm;
