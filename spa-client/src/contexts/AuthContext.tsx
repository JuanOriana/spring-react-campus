import React from "react";
import { internalAuthProvider } from "../scripts/auth";
import { UserModel } from "../types";

interface AuthContextType {
  user: UserModel | null;
  setUser: (user: UserModel | null) => void;
  signin: (user: UserModel, callback: VoidFunction) => void;
  signout: (callback: VoidFunction) => void;
}

const AuthContext = React.createContext<AuthContextType>(null!);

export function AuthProvider({ children }: { children: React.ReactNode }) {
  let [user, setUser] = React.useState<UserModel | null>(null);

  let signin = (newUser: UserModel, callback: VoidFunction) => {
    return internalAuthProvider.signin(() => {
      setUser(newUser);
      if (!localStorage.getItem("user"))
        localStorage.setItem("user", JSON.stringify(newUser));
      localStorage.setItem("token", newUser.token!);
      localStorage.setItem("isAdmin", newUser.admin ? "true" : "false");
      callback();
    });
  };

  let signout = (callback: VoidFunction) => {
    return internalAuthProvider.signout(() => {
      setUser(null);
      localStorage.removeItem("user");
      localStorage.removeItem("token");
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
