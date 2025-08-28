import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import ProtectedRoute from "./routes/ProtectedRoute.tsx";
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import Login from "./pages/auth/Login";
import AdminDashboard from "./pages/admin/AdminDashboard.tsx";
import Profile from "./pages/common/Profile.tsx";
import { JSX } from "react";
import { useAuthInterceptor } from "./hooks/UseAuthInterceptor.tsx";
import RoleBasedLayout from "./layouts/RoleBasedLayout.tsx";
import WorkerDashboard from "./pages/worker/WorkerDashboard.tsx";
import Home from "./pages/client/Home.tsx";
import Cabins from "./pages/client/Cabins.tsx";
import { AuthProvider } from "./contexts/AuthContext";
import './App.css';
import Tours from "./pages/client/Tours.tsx";
import Contact from "./pages/client/Contact.tsx";
import Register from "./pages/auth/Register.tsx";
import NoAuthorized from "./pages/common/NoAuthorized.tsx";
import NotFound from "./pages/common/NotFound.tsx";

const App = (): JSX.Element => {
  useAuthInterceptor();
  return (
    <>
      <BrowserRouter>
        <AuthProvider>
          <Routes>
            <Route element={<RoleBasedLayout />}>
              {/* public */}
              <Route path="/" element={<Navigate to="/home" replace />} />
              <Route path="/home" element={<Home />} />
              <Route path="/cabins" element={<Cabins />} />
              <Route path="/tours" element={<Tours />} />
              <Route path="/contact" element={<Contact />} />
              <Route path="/login" element={<Login />} />
              <Route path="/register" element={<Register />} />
              <Route path="/no-authorized" element={<NoAuthorized />} />
              <Route path="*" element={<NotFound />} />
              {/* protected: profile for any authenticated role */}
              <Route element={<ProtectedRoute rolesAlloweds={["ADMIN","WORKER","USER"]} />}>
                <Route path="/perfil" element={<Profile />} />
              </Route>

              {/* protected: admin only */}
              <Route element={<ProtectedRoute rolesAlloweds={["ADMIN"]} />}>
                <Route path="/admin/dashboard" element={<AdminDashboard />} />
              </Route>

              {/* protected: worker only */}
              <Route element={<ProtectedRoute rolesAlloweds={["WORKER"]} />}>
                <Route path="/worker/dashboard" element={<WorkerDashboard />} />
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
