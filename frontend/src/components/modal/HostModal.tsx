import { useImperativeHandle, useRef } from "react";
import { Link } from "react-router-dom";

type HostModalHandle = {
  open: () => void;
  close: () => void;
};

type HostModalProps = {
  ref: React.RefObject<HostModalHandle | null>;
};

export default function HostModal({ ref }: HostModalProps) {
  const dialogRef = useRef<HTMLDialogElement>(null);

  useImperativeHandle(ref, () => ({
    open: () => dialogRef.current?.showModal(),
    close: () => dialogRef.current?.close(),
  }));

  return (
    <dialog
      ref={dialogRef}
      className="mx-auto my-auto w-[650px] max-w-[90%] rounded-2xl shadow-2xl border border-indigo-100 backdrop:bg-black/60 backdrop:backdrop-blur-sm overflow-hidden"
    >
      <div className="h-[250px] bg-gradient-to-br from-white to-indigo-50 flex flex-col justify-between p-6">
        {/* Header */}
        <div className="text-center">
          <h2 className="text-2xl font-bold text-indigo-700">Host a Quiz</h2>
          <p className="text-gray-600 mt-1 text-sm">
            Create a new quiz or use one you’ve made before.
          </p>
        </div>

        {/* Buttons */}
        <div className="flex justify-center gap-6">
          <button className="px-6 py-3 bg-indigo-500 hover:bg-indigo-600 text-white font-semibold rounded-lg shadow-md transition-all focus:outline-none focus:ring-2 focus:ring-indigo-400 focus:ring-offset-2">
            Create New Quiz
          </button>
          <Link to="/create/question" className="px-6 py-3 bg-white hover:bg-gray-50 text-indigo-600 border border-indigo-300 font-semibold rounded-lg shadow-sm transition-all focus:outline-none focus:ring-2 focus:ring-indigo-400 focus:ring-offset-2">
            Create question
          </Link>
        </div>

        {/* Close */}
        <div className="flex justify-center">
          <button
            onClick={() => dialogRef.current?.close()}
            className="w-3/4 py-3 bg-indigo-100 hover:bg-indigo-200 text-indigo-700 font-medium rounded-md transition-all shadow-sm focus:outline-none focus:ring-2 focus:ring-indigo-300 focus:ring-offset-2"
          >
            Close
          </button>
        </div>
      </div>
    </dialog>
  );
}
