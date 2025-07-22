import { useEffect } from 'react';
import { getCurrentUser } from '../../api/authAPI';


const  Home = () => {

  const fetchUser = async () => {
      try {
        const user = await getCurrentUser();
        console.log('Current user:', user);
      } catch (err) {
        console.error('Error fetching current user:', err);
      }
    };
  
  return (
    <main className="min-h-screen bg-gradient-to-br from-blue-50 to-indigo-100 flex flex-col items-center justify-center px-4 text-center">
      {/* Hero Section */}
      <h1 className="text-5xl font-extrabold text-gray-800 mb-4">
        Welcome to Quiz Galaxy
      </h1>
      <p className="text-lg text-gray-700 max-w-2xl mb-8">
        Host fun and interactive quizzes for your friends, colleagues, or students.  
        Or join quizzes created by others and test your knowledge in real time!
      </p>

      {/* Actions */}
      <div className="flex flex-col sm:flex-row gap-4 justify-center mb-12">
        <button onClick={fetchUser} className="px-6 py-3 bg-indigo-500 hover:bg-indigo-600 text-white font-semibold rounded-lg shadow-md transition-all focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-400">
          Host a Quiz
        </button>
        <button className="px-6 py-3 bg-white border border-indigo-300 text-indigo-600 font-semibold rounded-lg shadow-sm hover:bg-indigo-50 transition-all focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-400">
          Join a Quiz
        </button>
      </div>

      {/* Features Section */}
      <div className="grid sm:grid-cols-2 gap-8 max-w-4xl">
        <div className="p-6 bg-indigo-100/50 rounded-xl">
          <h2 className="text-xl font-semibold text-indigo-700 mb-2">
            🏗️ Easy Hosting
          </h2>
          <p className="text-gray-700">
            Create your quiz in minutes with our simple and intuitive interface.
          </p>
        </div>
        <div className="p-6 bg-indigo-100/50 rounded-xl">
          <h2 className="text-xl font-semibold text-indigo-700 mb-2">
            🎯 Fun Participation
          </h2>
          <p className="text-gray-700">
            Join quizzes, compete with others, and see how you rank instantly.
          </p>
        </div>
      </div>
    </main>
  );
}

export default Home;