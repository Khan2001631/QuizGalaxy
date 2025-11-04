import { createContext, useEffect, useState, type ReactNode } from "react";

export type User = {
    id: number;
    username: string;
    email: string;
    firstName: string;
    lastName: string;
    avatarUrl?: string;
    userType?: string;
    userLevel?: string;
    quizzesAttendedCount?: number;
    isActive?: boolean;
    emailVerified?: boolean;
}

type UserContextType = {
    user: User | null;
    setUser: (user: User | null) => void;
};

const UserContext = createContext<UserContextType | undefined>(undefined);

export const UserProvider = ({ children }: { children: ReactNode }) => {
    const [user, setUser] = useState<User | null>(null);

    // Optional: Load user from cookies/localStorage on mount
    useEffect(() => {
        const userFromStorage = localStorage.getItem('user');
        if (userFromStorage) {
        setUser(JSON.parse(userFromStorage));
        }
    }, []);

    // Optional: Sync user to localStorage
    useEffect(() => {
        if (user) {
        localStorage.setItem('user', JSON.stringify(user));
        } else {
        localStorage.removeItem('user');
        }
    }, [user]);

    return (
        <UserContext.Provider value={{ user, setUser }}>
        {children}
        </UserContext.Provider>
    );
};

export default UserContext;