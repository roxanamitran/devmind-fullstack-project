import { Button } from "react-bootstrap";
import apiClient from "../api/axios";
import { jwtDecode } from "jwt-decode";

function AppointmentDetails({ appointment, setRefresh }) {
  const token = localStorage.getItem("jsonwebtoken");
  let role = null;

  if (token) {
    const decodedToken = jwtDecode(token);
    console.log(decodedToken);
    role = decodedToken.authorities[0];
  }

  const handleDelete = async () => {
    try {
      await apiClient.post(
        `/appointments/${appointment.id}/CANCELLED`,
        {},
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("jsonwebtoken")}`
          }
        }
      );
      setRefresh((refresh) => !refresh);
    } catch (error) {
      console.error("Nu s-a putut anula programarea. Incercati mai tarziu"),
        error;
    }
  };

  return (
    <>
      <section>
        <div className="container px-4 px-lg-5 mt-5">
          <div
            className="card h-100"
            style={{
              minWidth: "400px",
              maxWidth: "400px",
              padding: "20px",
              border: "none",
              backgroundColor: "#f9f9f9",
              borderRadius: "10px"
            }}
          >
            <h1
              className="fw-bold"
              style={{ fontSize: "1.8rem", color: "#333" }}
            >
              Salon: {appointment.salon.name}
            </h1>
            {role === "ROLE_CUSTOMER" && (
              <h3
                className="fw-bolder"
                style={{
                  fontSize: "1.4rem",
                  color: "#555",
                  marginBottom: "10px"
                }}
              >
                Angajat: {appointment.employee.firstName}
                {appointment.employee.lastName}
              </h3>
            )}
            {role === "ROLE_EMPLOYEE" && (
              <h3
                className="fw-bolder"
                style={{
                  fontSize: "1.4rem",
                  color: "#555",
                  marginBottom: "10px"
                }}
              >
                Client: {appointment.customer.firstName}{" "}
                {appointment.customer.lastName}
              </h3>
            )}

            <h5 style={{ color: "#666", marginBottom: "8px" }}>
              Durata: {appointment.salonToSalonOffer.duration} minute
            </h5>
            <h5 style={{ color: "#666", marginBottom: "8px" }}>
              Ziua: {new Date(appointment.startDate).toLocaleString()}
            </h5>
            <h5 style={{ color: "#666", marginBottom: "16px" }}>
              Status: <strong>{appointment.status}</strong>
            </h5>

            {appointment.status === "ACCEPTED" && (
              <Button
                variant="danger"
                onClick={handleDelete}
                style={{
                  width: "100%", // Buton pe toată lățimea cardului
                  padding: "10px",
                  fontSize: "1.1rem",
                  borderRadius: "5px",
                  fontWeight: "bold",
                  transition: "background-color 0.3s" // Efect la hover
                }}
              >
                Anuleaza programarea
              </Button>
            )}
          </div>
        </div>
      </section>
    </>
  );
}

export default AppointmentDetails;
