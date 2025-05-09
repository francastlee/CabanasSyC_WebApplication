import tokenMiddleware from "./TokenMiddleWare";

export const loginRequest = async (email: string, password: string) => {
  const { data } = await tokenMiddleware.post("/auth/login", { email, password });
  return data.data.accessToken;
};

export const fetchUserData = async (token: string) => {
  const { data } = await tokenMiddleware.get("/users/auth", {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
  return data.data;
};
