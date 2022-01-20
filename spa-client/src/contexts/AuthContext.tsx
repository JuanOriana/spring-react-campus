import React from "react";
import { internalAuthProvider } from "../scripts/auth";

interface AuthContextType {
  user: any;
  setUser: (user: string) => void;
  signin: (user: string, callback: VoidFunction) => void;
  signout: (callback: VoidFunction) => void;
}

const AuthContext = React.createContext<AuthContextType>(null!);

export function AuthProvider({ children }: { children: React.ReactNode }) {
  let [user, setUser] = React.useState<any>(null);

  let signin = (newUser: string, callback: VoidFunction) => {
    return internalAuthProvider.signin(() => {
      setUser(newUser);
      localStorage.setItem("user", newUser);
      callback();
    });
  };

  let signout = (callback: VoidFunction) => {
    return internalAuthProvider.signout(() => {
      setUser(null);
      localStorage.setItem("user", "");
      callback();
    });
  };

  let value = { user, setUser, signin, signout };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth() {
  return React.useContext(AuthContext);
}
