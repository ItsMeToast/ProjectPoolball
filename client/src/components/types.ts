export type Player = {
  id: number,
  firstName: string,
  lastName: string,
  age: number,
  playstyle: string,
  trait: string,
  potential: number,
  stats: Statline
}

export type Statline = {
  accuracy: number,
  blocking: number,
  endurance: number,
  explosiveness: number,
  elligence: number,
  power: number,
  size: number,
  speed: number,
  injury: number,
}