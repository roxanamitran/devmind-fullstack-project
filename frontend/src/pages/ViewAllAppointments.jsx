import { Row, Col } from "react-bootstrap";
import apiClient from "../api/axios";
import { useEffect, useState } from "react";
import AppointmentDetails from "./AppointmentDetails";

function ViewAllAppointments() {
  const [appointments, setAppointments] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [refresh, setRefresh] = useState(false);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await apiClient.get("/appointments", {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("jsonwebtoken")}`
          }
        });
        setAppointments(response.data);
      } catch (error) {
        setError("Error fetching data. Please try again.");
        console.error("Error fetching data:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [refresh]);

  if (loading) {
    return <div>Loading appointments...</div>;
  }

  if (error) {
    return <div>{error}</div>;
  }

  return (
    <>
      <h1 className="centrat">Programarile tale</h1>

      <div>
        {appointments.length === 0 ? (
          <p>No appointments available.</p>
        ) : (
          <Row>
            {appointments.map((appointment) => (
              <Col sm={12} md={6} lg={4} key={appointment.id}>
                <AppointmentDetails
                  appointment={appointment}
                  setRefresh={setRefresh}
                />
              </Col>
            ))}
          </Row>
        )}
      </div>
    </>
  );
}

export default ViewAllAppointments;
