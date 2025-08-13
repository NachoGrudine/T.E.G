export interface Player {
  id: string
  name: string
  color: string
  troops: number
  cards: Card[]
}

export interface Card {
  id: string
  country: string
  continent: string
  imageUrl?: string
}

export interface Territory {
  id: string
  name: string
  continent: string
  troops: number
  ownerId: string
  neighbors: string[]
}

export interface GameState {
  currentPlayer: string
  phase: "Agrupar" | "Atacar" | "Refuerzo"
  turn: number
  players: Player[]
  territories: Territory[]
  objective: string
}

export interface ChatMessage {
  id: string
  playerId: string
  playerName: string
  message: string
  timestamp: Date
}
