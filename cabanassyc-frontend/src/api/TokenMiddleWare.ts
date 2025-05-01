import axios, { AxiosInstance, InternalAxiosRequestConfig } from "axios";

const tokenMiddleware: AxiosInstance = axios.create({
  baseURL: import.meta.env.VITE_BACKEND_URL as string,
});

tokenMiddleware.interceptors.request.use(
  (config: InternalAxiosRequestConfig): InternalAxiosRequestConfig => {
    const token = localStorage.getItem("token");
    if (token && config.headers) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error: any): Promise<any> => Promise.reject(error)
);

export default tokenMiddleware;