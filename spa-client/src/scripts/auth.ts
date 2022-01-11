/**
 * This represents some generic auth provider API, like Firebase.
 */
const internalAuthProvider = {
  isAuthenticated: false,
  signin(callback: VoidFunction) {
    internalAuthProvider.isAuthenticated = true;
    setTimeout(callback, 100); // fake async
  },
  signout(callback: VoidFunction) {
    internalAuthProvider.isAuthenticated = false;
    setTimeout(callback, 100);
  },
};

export { internalAuthProvider };
