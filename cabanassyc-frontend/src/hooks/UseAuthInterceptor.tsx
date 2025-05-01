import { useEffect } from "react";
import { useAuth } from "../hooks/UseAuth.ts";
import tokenMiddleware from "../api/TokenMiddleWare.ts";
import { toast } from "react-toastify";

export const useAuthInterceptor = () => {
  const { logout } = useAuth();

  useEffect(() => {
    const interceptor = tokenMiddleware.interceptors.response.use(
      (response) => response,
      (error) => {
        if (error.response && error.response.status === 401) {
          toast.error('Sesión expirada. Por favor, inicia sesión nuevamente.');
          logout();
        }
        return Promise.reject(error);
      }
    );

    return () => {
      tokenMiddleware.interceptors.response.eject(interceptor);
    };
  }, [logout]);
};