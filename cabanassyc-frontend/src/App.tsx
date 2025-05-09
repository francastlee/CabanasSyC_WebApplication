import { BrowserRouter, Routes, Route } from "react-router-dom";
import ProtectedRoute from "./routes/ProtectedRoute.tsx";
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import Login from "./pages/auth/Login";
import AdminDashboard from "./pages/admin/AdminDashboard.tsx";
import Profile from "./pages/common/Profile.tsx";
//import NoAuthorized from "./pages/auth/NoAuthorized";
import { JSX } from "react";
import { useAuthInterceptor } from "./hooks/UseAuthInterceptor.tsx";
import RoleBasedLayout from "./layouts/RoleBasedLayout.tsx";
import WorkerDashboard from "./pages/worker/WorkerDashboard.tsx";
import Home from "./pages/client/Home.tsx";
import Cabins from "./pages/client/Cabins.tsx";
import { AuthProvider } from "./contexts/AuthContext";
import './App.css';
const App = (): JSX.Element => {
  useAuthInterceptor();
  return (
    <>
    <BrowserRouter>
      <AuthProvider>
          <Routes>
            {/* Página de Login */}
            <Route path="/login" element={<Login />} />
            
            {/* Rutas protegidas */}
            <Route element={<ProtectedRoute rolesAlloweds={["ADMIN", "WORKER", "USER"]} />}>
              <Route element={<RoleBasedLayout />}>
              <Route path="/home" element={<Home />} />
              <Route path="/perfil" element={<Profile />} />
              <Route path="/cabins" element={<Cabins />} />

              {/* Admin */}
              <Route path="/admin/dashboard" element={<AdminDashboard />} />

              {/* Trabajador */}
              <Route path="/worker/dashboard" element={<WorkerDashboard />} />

                  {/* Otras rutas aquí */}
              </Route>
            </Route>
          </Routes>
        </AuthProvider>
      </BrowserRouter>
      <ToastContainer 
        position="top-right"
        autoClose={3000}
        hideProgressBar={false}
        newestOnTop
        closeOnClick
        pauseOnHover
        draggable
      />
    </>
  );
};

export default App;