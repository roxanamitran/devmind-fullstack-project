import { ListGroup } from "react-bootstrap";
import apiClient from "../api/axios";
import { useEffect, useState } from "react";
import AppointmentDetails from "./AppointmentDetails";

function ViewAllAppointments() {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await apiClient.get("/appointments", {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("jsonwebtoken")}`
          }
        });
        setData(response.data);
      } catch (error) {
        setError("Error fetching data. Please try again.");
        console.error("Error fetching data:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, []);

  if (loading) {
    return <div>Loading appointments...</div>;
  }

  if (error) {
    return <div>{error}</div>;
  }

  return (
    <ListGroup>
      <h1>Programarile tale</h1>
      {data.length === 0 ? (
        <p>No appointments available.</p>
      ) : (
        data.map((appointment) => (
          <AppointmentDetails key={appointment.id} appointment={appointment} />
        ))
      )}
    </ListGroup>
  );
}

export default ViewAllAppointments;
