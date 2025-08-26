import { AxiosResponse } from "axios";
import tokenMiddleware from "./TokenMiddleWare";

export interface ContactFormData {
  firstName: string;
  lastName: string;
  email: string;
  phone: string;
  message: string;
  date: string;
}

export interface ContactResponse {
  success: boolean;
  message?: string;
}

export const sendContactForm = async (
  data: ContactFormData
): Promise<ContactResponse> => {
  try {
    const response: AxiosResponse<ContactResponse> = await tokenMiddleware.post("/contacts", data);
    return response.data;
  } catch (error: any) {
    console.error("Failed to send contact form:", error); 
    throw new Error(
      error?.response?.data?.message || "Unexpected error while sending contact form."
    );
  }
};
