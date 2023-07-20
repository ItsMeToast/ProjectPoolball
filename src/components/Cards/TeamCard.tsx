import "./TeamCard.css";

function TeamCard(props: any) {
  return (
    <div className="teamcard container text-center align-items-center px-0 mx-0">
      <img className="teamcard-logo no-interact" src={"../public/" + props.code + ".png"} />
    </div>
  );
}

export default TeamCard;
