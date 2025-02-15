import { Navbar, Nav, Container } from "react-bootstrap";
import { NavLink } from "react-router-dom";
function NavBarComponent() {
  return (
    <Navbar bg="dark" data-bs-theme="dark">
      <Container>
        <Navbar.Brand>BeautyHub</Navbar.Brand>
        <Nav className="me-auto">
          <Nav.Link as={NavLink} to="/">
            Acasa
          </Nav.Link>
          <Nav.Link as={NavLink} to="/login">
            Logare
          </Nav.Link>
        </Nav>
      </Container>
    </Navbar>
  );
}

export default NavBarComponent;
