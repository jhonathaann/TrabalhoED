import os
import fitz  # PyMuPDF

# Diretório onde estão os PDFs
input_directory = "C:/Users/VICTUS/Desktop/facul/4°semestre/estrutura-de-dados/TrabalhoED/arquivos-PDF"
output_directory = "C:/Users/VICTUS/Desktop/facul/4°semestre/estrutura-de-dados/TrabalhoED/conveterArquivos"

# Garantir que o diretório de saída existe
os.makedirs(output_directory, exist_ok=True)

# Listar e processar todos os PDFs no diretório
for filename in os.listdir(input_directory):
    if filename.lower().endswith(".pdf"):  # Verificar extensão PDF
        pdf_path = os.path.join(input_directory, filename)
        txt_path = os.path.join(output_directory, f"{os.path.splitext(filename)[0]}.txt")
        
        try:
            
            # Abrindo o PDF
            pdf_document = fitz.open(pdf_path)
            
            # Extraindo texto e salvando em um arquivo .txt
            with open(txt_path, "w", encoding="utf-8") as txt_file:
                for page_num in range(pdf_document.page_count):
                    page = pdf_document.load_page(page_num)
                    text = page.get_text()
                    txt_file.write(text + "\n")
            
            print(f"Convertido: {filename} -> {os.path.basename(txt_path)}")
        
        except Exception as e:
            print(f"Erro ao processar {filename}: {e}")
        
        finally:
            if 'pdf_document' in locals() and not pdf_document.is_closed:
                pdf_document.close()
