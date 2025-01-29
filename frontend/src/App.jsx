import { Route, Routes } from "react-router-dom";
import Home from "./pages/Home";
import Salons from "./pages/Salons";
import Offers from "./pages/Offers";
import SalonDetails from "./pages/SalonDetails";
import Appointment from "./pages/Appointment";

function App() {
  return (
    <Routes>
      <Route path="/" element={<Home></Home>}></Route>
      <Route path="/home" element={<Home></Home>}></Route>
      <Route path="/salons" element={<Salons></Salons>}></Route>
      <Route path="/salons/:id" element={<SalonDetails></SalonDetails>}></Route>
      <Route path="/salonOffers" element={<Offers></Offers>}></Route>
      <Route path="/appointment" element={<Appointment />}></Route>
    </Routes>
  );
}

export default App;
