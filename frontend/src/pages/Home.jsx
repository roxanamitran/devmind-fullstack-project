import Header from "../components/Header";
import Offers from "./Offers";
function Home() {
  return (
    <>
      <Header></Header>
      <section className="py-0">
        <div className="custom-carousel-container">
          <Offers />
        </div>
      </section>
    </>
  );
}

export default Home;
