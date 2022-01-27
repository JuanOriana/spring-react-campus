import React from "react";
import { internalAuthProvider } from "../scripts/auth";

interface User {
  name: string;
  isAdmin: boolean;
}

interface AuthContextType {
  user: User | null;
  setUser: (user: User | null) => void;
  signin: (user: string, callback: VoidFunction) => void;
  signout: (callback: VoidFunction) => void;
}

const AuthContext = React.createContext<AuthContextType>(null!);

export function AuthProvider({ children }: { children: React.ReactNode }) {
  let [user, setUser] = React.useState<User | null>(null);

  let signin = (newUser: string, callback: VoidFunction) => {
    return internalAuthProvider.signin(() => {
      const isAdmin = false;
      setUser({ name: newUser, isAdmin: isAdmin });
      localStorage.setItem("user", newUser);
      localStorage.setItem("isAdmin", isAdmin ? "true" : "false");
      callback();
    });
  };

  let signout = (callback: VoidFunction) => {
    return internalAuthProvider.signout(() => {
      setUser(null);
      localStorage.removeItem("user");
      localStorage.removeItem("isAdmin");
      callback();
    });
  };

  let value = { user, setUser, signin, signout };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth() {
  return React.useContext(AuthContext);
}
