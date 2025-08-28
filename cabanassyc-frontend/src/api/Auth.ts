import axios from "axios";

const publicApi = axios.create({
  baseURL: import.meta.env.VITE_API_URL || "http://localhost:8080",
});

export const loginRequest = async (email: string, password: string) => {
  const { data } = await publicApi.post("/auth/login", { email, password });
  return data.data.accessToken;
};

export const fetchUserData = async (token: string) => {
  const { data } = await publicApi.get("/users/auth", {
    headers: { Authorization: `Bearer ${token}` },
  });
  return data.data;
};

export interface RegisterPayload {
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  hourlyRate: number; 
}

export interface RegisterResponse {
  message: string;
}

export const registerRequest = async (
  payload: RegisterPayload
): Promise<RegisterResponse> => {
  try {
    const { data } = await publicApi.post<RegisterResponse>(
      "/auth/register",
      payload
    );
    return data;
  } catch (err: any) {
    const msg =
      err?.response?.data?.message ||
      err?.message ||
      "Registration failed.";
    throw new Error(msg);
  }
};
