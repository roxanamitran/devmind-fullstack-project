import { useEffect, useState } from "react";
import { Button, Form, Table } from "react-bootstrap";
import Calendar from "react-calendar/dist/cjs/Calendar.js";
import apiClient from "../api/axios";

function NewAppointment() {
  const [date, setDate] = useState(null);
  const [employees, setEmployees] = useState([]);
  const [error, setError] = useState(null);
  const [isDateModalOpen, setIsDateModalOpen] = useState(false);
  const [isAvailableSlotsModalOpen, setIsAvailableSlotsModalOpen] =
    useState(false);
  const [employeeId, setEmployeeId] = useState("");
  const [availableSlots, setAvailableSlots] = useState([]);
  const [selectedSlot, setSelectedSlot] = useState("");

  const queryParams = new URLSearchParams(location.search);
  const salonId = queryParams.get("salon_id");
  const salonOfferId = queryParams.get("salon_offer_id");

  useEffect(() => {
    const fetchEmployees = async () => {
      try {
        const response = await apiClient.get(`/salons/${salonId}/employees`);
        console.log(response);
        setEmployees(response.data);
      } catch (err) {
        console.error("Error fetching employees:", err);
        setError("Nu am putut încărca lista de angajați.");
      }
    };

    fetchEmployees();
  }, []);

  function handleEmployeeSelection(id) {
    const dateModalValue = !isDateModalOpen;
    setIsDateModalOpen(dateModalValue);

    if (dateModalValue) {
      setEmployeeId(id);
    } else {
      setEmployeeId("");
      setIsAvailableSlotsModalOpen(false);
    }
  }

  const onDateChanged = async () => {
    const resp = await apiClient.post("/appointments/available-slots", {
      salonId,
      employeeId,
      date: date.toISOString()
    });

    if (resp.data.length > 0) {
      setSelectedSlot(resp.data[0]);
    }
    setAvailableSlots(resp.data);
    setIsAvailableSlotsModalOpen(true);
  };

  const handleSaveAppointment = async () => {
    try {
      await apiClient.post("/appointments", {
        salon: {
          id: salonId
        },
        salonToSalonOffer: {
          id: salonOfferId
        },
        customer: {},
        employee: {
          id: employeeId
        },
        startDate: date
      });
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <>
      <>Alege profesionistul care ti se potriveste</>

      <Table striped bordered hover>
        <thead>
          <tr style={{ display: "table", width: "100%", tableLayout: "fixed" }}>
            <th>Nume</th>
            <th>Fotografia</th>
            <th>Alege profesionistul</th>
          </tr>
        </thead>
        <tbody
          style={{
            display: "block",
            maxHeight: "300px",
            overflow: "auto",
            width: "100%"
          }}
        >
          {employees.map((employee) => (
            <tr
              key={employee.id}
              style={{ display: "table", width: "100%", tableLayout: "fixed" }}
            >
              <td>
                {employee.firstName} {employee.lastName}
              </td>
              <td>
                <img
                  src={employee.photoUrl}
                  alt={employee.firstName}
                  style={{
                    width: "90px",
                    height: "90px"
                  }}
                />
              </td>
              <td>
                <Button onClick={handleEmployeeSelection}>Alege</Button>
              </td>
            </tr>
          ))}
        </tbody>
      </Table>
      {isDateModalOpen && (
        <div>
          <Calendar onChange={onDateChanged} value={date} />
        </div>
      )}
      {isAvailableSlotsModalOpen && (
        <div>
          <h1>Ore disponibile</h1>
          <Form.Select
            type="text"
            value={selectedSlot}
            onChange={(e) => setSelectedSlot(e.target.value)}
          >
            {availableSlots.map((availableSlot) => (
              <option key={availableSlot} value={availableSlot}>
                {availableSlot}
              </option>
            ))}
          </Form.Select>
        </div>
      )}
    </>
  );
}

export default NewAppointment;
