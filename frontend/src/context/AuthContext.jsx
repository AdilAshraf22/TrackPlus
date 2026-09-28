import { createContext, useContext, useState } from "react";
import { jwtDecode } from "jwt-decode";

const AuthContext = createContext();

export function AuthProvider({ children }) {
    const [token, setToken] = useState(
        localStorage.getItem("token")
    );

    const getUserFromToken = (jwtToken) => {
        if (!jwtToken) {
            return null;
        }

        try {
            return jwtDecode(jwtToken);
        } catch (error) {
            return null;
        }
    };

    const [user, setUser] = useState(
        getUserFromToken(token)
    );

    const login = (jwtToken) => {
        localStorage.setItem("token", jwtToken);

        setToken(jwtToken);
        setUser(getUserFromToken(jwtToken));
    };

    const logout = () => {
        localStorage.removeItem("token");

        setToken(null);
        setUser(null);
    };

    const isAuthenticated = !!token && !!user;

    return (
        <AuthContext.Provider
            value={{
                token,
                user,
                login,
                logout,
                isAuthenticated,
            }}
        >
            {children}
        </AuthContext.Provider>
    );
}

export function useAuth() {
    return useContext(AuthContext);
}