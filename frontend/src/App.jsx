import { Route, Routes } from "react-router-dom";
import Home from "./pages/Home";
import Salons from "./pages/Salons";
import Offers from "./pages/Offers";
import SalonDetails from "./pages/SalonDetails";
import Appointment from "./pages/Appointment";
import AddSalon from "./pages/AddSalon";
import Login from "./pages/Login";
import CreateUser from "./pages/CreateUser";
import ViewAllAppointments from "./pages/ViewAllAppointments";
import ManageSalonOffers from "./pages/ManageSalonOffers.jsx";
import NavBarComponent from "./components/NavBarComponent.jsx";
import Footer from "./components/Footer.jsx";
import ManageEmployees from "./pages/ManageEmployees.jsx";
import NewAppointment from "./pages/NewAppointment.jsx";

function App() {
  return (
    <div className="app-container">
      <div className="top-component">
        <NavBarComponent />
      </div>

      <div className="scrollable-content">
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/home" element={<Home />} />
          <Route path="/salons" element={<Salons />} />
          <Route path="/salons/:id" element={<SalonDetails />} />
          <Route path="/salonOffers" element={<Offers />} />
          <Route path="/appointment" element={<Appointment />} />
          <Route path="/add_new_salon" element={<AddSalon />} />
          <Route path="/edit_salon/:id" element={<AddSalon />} />
          <Route path="/login" element={<Login />} />
          <Route path="/create_user" element={<CreateUser />} />
          <Route path="/my_appointments" element={<ViewAllAppointments />} />
          <Route path="/add_salon_offer" element={<ManageSalonOffers />} />
          <Route path="/asign_employees" element={<ManageEmployees />}></Route>
          <Route path="/new_appointment" element={<NewAppointment />}></Route>
        </Routes>
      </div>

      <div className="bottom-component">
        <Footer />
      </div>
    </div>
  );
}

export default App;
