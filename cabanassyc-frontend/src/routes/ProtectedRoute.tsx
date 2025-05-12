import { useContext } from "react";
import { Navigate, Outlet } from "react-router-dom";
import { AuthContext } from "../contexts/AuthContext";

interface ProtectedRouteProps {
  rolesAlloweds: string[];
}

const ProtectedRoute = ({ rolesAlloweds }: ProtectedRouteProps) => {
  const { user, loading, authenticated } = useContext(AuthContext);

  if (loading) {
    return (
      <div className="flex items-center justify-center min-h-screen">
        <p className="text-gray-500 text-xl">Loading...</p>
      </div>
    );
  }

  if (!authenticated) {
    return <Navigate to="/login" />;
  }

  if (!rolesAlloweds.includes(user?.role || "")) {
    return <Navigate to="/no-autorizado" />;
  }

  return <Outlet />;
};

export default ProtectedRoute;