import tokenMiddleware from "./TokenMiddleWare";

export interface cabinImages {
  id: number;
  cabinId: number;
  url: string;
  isCover: boolean;
  state: boolean;
}

export interface Equipment {
  id: number;
  name: string;
  state: boolean;
}

export interface CabinType {
  id: number;
  name: string;
  capacity: number;
  price: number;
  state: boolean;
}

export interface Cabin {
  id: number;
  name: string;
  state: boolean;
  cabinType: CabinType;
  equipments: Equipment[];
  cabinImages: cabinImages[];
}

export const getCabinsWithDetails = async (): Promise<Cabin[]> => {
  const response = await tokenMiddleware.get("/cabins/details");
  return response.data.data;
};
