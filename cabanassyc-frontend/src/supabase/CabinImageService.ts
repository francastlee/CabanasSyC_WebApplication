import { supabase } from "./SupabaseClient";

export async function uploadImage(file: File, folder: string) {
  const fileName = `${Date.now()}-${file.name}`;

  const { error } = await supabase.storage
    .from(folder)
    .upload(fileName, file);

  if (error) throw error;

  return `${import.meta.env.VITE_SUPABASE_URL}/storage/v1/object/public/${folder}/${fileName}`;
}

export async function deleteImage(folder: string, fileName: string) {
  const { error } = await supabase.storage
    .from(folder)
    .remove([fileName]);

  if (error) throw error;
}
