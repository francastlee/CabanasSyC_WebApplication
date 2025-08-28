import { Link, useLocation } from "react-router-dom";
import { useTranslation } from "react-i18next";

export default function NoAuthorized() {
  const location = useLocation();
  const { t } = useTranslation("errors");

  return (
    <div className="min-h-screen flex items-center justify-center p-6">
      <div className="max-w-md w-full bg-[#4B2A1F] shadow-lg rounded-xl p-8 text-center">
        <h1 className="text-5xl font-bold text-red-600 mb-2">403</h1>
        <h2 className="text-2xl font-semibold mb-3 text-white">
          {t("noAuthorized.title")}
        </h2>
        <p className="text-gray-400 mb-6">
          {t("noAuthorized.description", {
            page: location.state?.from ?? "this page"
          })}
        </p>
        <div className="flex gap-3 justify-center">
          <Link
            to="/home"
            className="px-4 py-2 rounded-lg bg-[#1a3a17] text-white hover:opacity-90"
          >
            {t("noAuthorized.goHome")}
          </Link>
        </div>
      </div>
    </div>
  );
}
