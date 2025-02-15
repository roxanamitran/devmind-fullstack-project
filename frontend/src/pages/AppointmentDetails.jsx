import { ListGroup } from "react-bootstrap";
function AppointmentDetails({ appointment }) {
  return (
    <ListGroup.Item key={appointment.id}>
      <div>Start Date: {appointment.startDate}</div>
      <div>End Date: {appointment.endDate}</div>
      <div>Status: {appointment.status}</div>
    </ListGroup.Item>
  );
}

export default AppointmentDetails;
