import { redirect } from "react-router-dom";

export async function postQuestionAction({ request }: any) {
  const formData = await request.formData();
  const raw = formData.get("questions");
  const questionsArray = raw ? JSON.parse(raw as string) : [];

  console.log(questionsArray);

  // Send raw array to backend
  await fetch("http://localhost:8080/api/create/questions", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    credentials: "include", 
    body: JSON.stringify(questionsArray),
  });

  return redirect("/");
}
