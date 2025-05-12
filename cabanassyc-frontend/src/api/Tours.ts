import tokenMiddleware from "./TokenMiddleWare";

export interface Tour {
  id: number;
  name: string;
  capacity: number;
  price: number;
  startTime: string;
  endTime: string;
  state: boolean;
}

export const getTours = async (): Promise<Tour[]> => {
  const response = await tokenMiddleware.get("/tours");
  return response.data.data;
};
