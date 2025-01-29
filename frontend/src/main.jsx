import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import "./styles.css";
import App from "./App.jsx";
import { BrowserRouter } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import NavBarComponent from "./components/NavBarComponent.jsx";
import Footer from "./components/Footer.jsx";
import "react-calendar/dist/Calendar.css";

createRoot(document.getElementById("root")).render(
  <StrictMode>
    <BrowserRouter>
      <NavBarComponent></NavBarComponent>
      <App />
      <Footer></Footer>
    </BrowserRouter>
  </StrictMode>
);
